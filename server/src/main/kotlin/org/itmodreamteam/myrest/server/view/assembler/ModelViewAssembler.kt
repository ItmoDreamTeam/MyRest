package org.itmodreamteam.myrest.server.view.assembler

interface ModelViewAssembler<in M, out V> {
    fun toView(model: M): V

    fun toListView(models: List<M>): List<V> = models.map { toView(it) }
}