package br.com.ite.animavita

import kotlin.random.Random

data class Animal (val name: String, val photo: Int, val type: String, var selected: Boolean = false, val id: Number = Random.nextInt(1,99999))