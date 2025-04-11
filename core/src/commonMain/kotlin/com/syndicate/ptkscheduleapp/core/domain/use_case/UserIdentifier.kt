package com.syndicate.ptkscheduleapp.core.domain.use_case

sealed class UserIdentifier {
    data class Student(val group: String) : UserIdentifier()
    data class Teacher(val name: String) : UserIdentifier()
}