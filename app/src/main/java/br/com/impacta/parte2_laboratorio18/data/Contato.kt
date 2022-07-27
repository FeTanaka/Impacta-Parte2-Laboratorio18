package br.com.impacta.parte2_laboratorio18.data

import org.json.JSONObject

data class Contato(
    val idContato: Long,
    val nome: String,
    val idade: Int
) {
    fun gerarJSON(): JSONObject {
        val json = JSONObject()
        json.put("idContato", this.idContato)
        json.put("nome", this.nome)
        json.put("idade", this.idade)
        return json
    }
}