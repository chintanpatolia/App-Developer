package com.supplementtracker.domain

import java.time.LocalDate

interface DateProvider {
    fun today(): LocalDate
}

object SystemDateProvider : DateProvider {
    override fun today(): LocalDate = LocalDate.now()
}
