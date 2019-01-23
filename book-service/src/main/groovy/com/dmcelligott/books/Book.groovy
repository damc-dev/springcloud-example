package com.dmcelligott.books

import groovy.transform.Canonical

@Canonical
class Book {
    String name
    String author

    @Override
    String toString() {
        return "$name by $author"
    }
}
