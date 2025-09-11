package io.github.incohesions.sanguo_armory.extensions

inline fun <T, reified R> Iterable<T>.flatMapArray(transform: (T) -> Array<out R>): Array<R> =
    this.flatMap { transform(it).asIterable() }.toTypedArray()

inline fun <T, reified R> Array<out T>.flatMapArray(transform: (T) -> Array<out R>): Array<R> =
    this.asIterable().flatMap { transform(it).asIterable() }.toTypedArray()
