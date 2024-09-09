package com.davidmerchan.core.model

fun Double.toMoney(): String {
    return "$ $this"
}