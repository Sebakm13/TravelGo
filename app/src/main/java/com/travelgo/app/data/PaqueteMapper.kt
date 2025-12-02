package com.travelgo.app.data

import com.travelgo.app.data.db.PaqueteLocal

fun PaqueteLocal.toDomain(): Paquete {
    return Paquete(
        id = this.id,
        titulo = this.titulo,
        descripcion = this.descripcion,
        precio = this.precio
    )
}

fun Paquete.toLocal(): PaqueteLocal {
    return PaqueteLocal(
        id = this.id,
        titulo = this.titulo,
        descripcion = this.descripcion,
        precio = this.precio
    )
}