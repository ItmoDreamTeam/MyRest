package org.itmodreamteam.myrest.server.view.assembler


interface ModelViewAssembler<in M, out V> {
    fun toView(model: M): V
}