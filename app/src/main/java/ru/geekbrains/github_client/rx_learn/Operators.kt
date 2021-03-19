package ru.geekbrains.github_client.rx_learn

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.schedulers.TestScheduler
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class Operators {

    fun exec() {
        Consumer(Producer()).exec()
    }

    class Producer {
        fun randomResultOperation(): Boolean {
            Thread.sleep(Random.nextLong(1000))
            return listOf(true, false, true)[Random.nextInt(2)]
        }

        fun just(): Observable<String> = Observable.just("1", "2", "3", "4", "5", "6")

        fun fromIterable() = Observable.fromIterable(listOf("3", "4", "5", "6", "3", "3", "5"))

        fun range() = Observable.range(5, 10)

        fun interval() = Observable.interval(1, TimeUnit.SECONDS)

        fun fromCallable() = Observable.fromCallable {
            val result = randomResultOperation()
            return@fromCallable "Result of very long operation: $result"
        }

        fun create() = Observable.create<String> { emitter ->
            for (i in 0..10) {
                randomResultOperation().let { emitter.onNext("Success: $it") }
            }
            emitter.onComplete()
        }

        fun create2() = Observable.create<String> { emitter ->
            for (i in 0..10) {
                randomResultOperation().let { emitter.onNext("Success2: $it") }
            }
            emitter.onComplete()
        }
    }

    class Consumer(val producer: Producer) {

        fun exec() {
            //execMap()
            //execFilter()
            //execDistinct()
            //execTake()
            //execSkip()
            //execMerge()
            //execDebounce()
            //execZip()
            execFlatmap()
        }

        fun execMap() {
            producer.fromIterable().map {
                it + it
            }.subscribe {
                println(it)
            }
        }

        fun execFilter() {
            producer.fromIterable().filter {
                it.toInt() % 2 == 0
            }.subscribe {
                println(it)
            }
        }

        fun execDistinct() {
            producer.fromIterable().distinct()
                .subscribe {
                    println(it)
                }
        }

        fun execTake() {
            producer.fromIterable()
                .take(2)
                .subscribe {
                    println(it)
                }
        }

        fun execSkip() {
            producer.fromIterable()
                .take(2)
                .subscribe {
                    println(it)
                }
        }

        fun execMerge() {
            producer.create().subscribeOn(Schedulers.newThread())
                .mergeWith(producer.create2())
                .subscribe {
                    println(it)
                }
        }

        fun execDebounce() {
            Observable.create<String> { emitter ->
                Thread.sleep(100)
                emitter.onNext("1")
                Thread.sleep(100)
                emitter.onNext("2")
                Thread.sleep(100)
                emitter.onNext("3")
                Thread.sleep(400)
                emitter.onNext("4")
            }.debounce(300, TimeUnit.MILLISECONDS)
                .subscribe {
                    println("onNext: $it")
                }
        }

        fun execZip() {
            val observable1 = Observable.just("1", "5").delay(1, TimeUnit.SECONDS)
                .doOnNext { println("1 emitted") }
            val observable2 = Observable.just("2", "6").delay(2, TimeUnit.SECONDS)
                .doOnNext { println("2 emitted") }
            val observable3 = Observable.just("3", "7").delay(3, TimeUnit.SECONDS)
                .doOnNext { println("3 emitted") }
            val observable4 =
                Observable.just("4").delay(4, TimeUnit.SECONDS).doOnNext { println("4 emitted") }

            Observable.zip(observable1, observable2, observable3, observable4, { t1, t2, t3, t4 ->
                return@zip listOf(t1, t2, t3, t4)
            }).subscribe {
                println("Zip:  $it")
            }
        }

        fun execFlatmap() {
            val testScheduler = TestScheduler()
            producer.just()
                .flatMap {
                    val delay = Random.nextInt(10).toLong()
                    return@flatMap Observable.just(it + "x")
                        .delay(delay, TimeUnit.SECONDS, testScheduler)
                }
                .toList()
                .subscribe({ list ->
                    println("onNext: $list")
                }, {
                    println("onError: ${it.message}")
                })

            testScheduler.advanceTimeBy(1, TimeUnit.MINUTES)
        }

        //switchMap
    }
}