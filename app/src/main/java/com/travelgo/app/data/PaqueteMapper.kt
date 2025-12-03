package com.travelgo.app.data

import com.travelgo.app.data.db.PaqueteLocal

fun PaqueteLocal.toDomain(): Paquete {
    return Paquete(
        id = this.id,
        nombre = this.nombre,
        destino = this.destino,
        descripcion = this.descripcion,
        precio = this.precio
    )
}

fun Paquete.toLocal(): PaqueteLocal {
    return PaqueteLocal(
        id = this.id,
        nombre = this.nombre,
        destino = this.destino,
        descripcion = this.descripcion,
        precio = this.precio
    )
}
