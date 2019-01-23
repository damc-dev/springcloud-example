package com.dmcelligott.books

import groovy.util.logging.Slf4j
import io.opentracing.Tracer

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

@Slf4j
class LoggingFilter implements Filter {

    Tracer tracer

    LoggingFilter(Tracer tracer) {
        this.tracer = tracer
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        if (servletRequest instanceof HttpServletRequest
                && servletResponse instanceof HttpServletResponse) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;

            HttpServletRequest requestToCache = new ContentCachingRequestWrapper(request);
            HttpServletResponse responseToCache = new ContentCachingResponseWrapper(response);
            chain.doFilter(requestToCache, responseToCache);
            String requestData = getRequestData(requestToCache) ?: "[empty]"
            String responseData = getResponseData(responseToCache) ?: "[empty]"
            def activeSpan = tracer.activeSpan()
            if (activeSpan) {
                activeSpan.setTag("request.payload", requestData)
                activeSpan.setTag("response.payload", responseData)
            } else {
                log.error("NO ACTIVE SPAN")
            }

            log.info("REQUEST PAYLOAD: {}", requestData)
            log.info("RESPONSE PAYLOAD: {}", responseData)
        } else {
            chain.doFilter(servletRequest, servletResponse);
        }
    }

    private static String getRequestData(final HttpServletRequest request) throws UnsupportedEncodingException {
        String payload = null;
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
            }
        }
        return payload;
    }

    private static String getResponseData(final HttpServletResponse response) throws IOException {
        String payload = null;
        ContentCachingResponseWrapper wrapper =
                WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                wrapper.copyBodyToResponse();
            }
        }
        return payload;
    }

    @Override
    public void destroy() {
    }
}