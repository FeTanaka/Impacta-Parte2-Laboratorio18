package br.com.impacta.parte2_laboratorio18

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.impacta.parte2_laboratorio18.data.Contato
import br.com.impacta.parte2_laboratorio18.databinding.FragmentHomeBinding
import org.json.JSONArray
import org.json.JSONObject


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Variável que vai armazenar o JSON gerado da nossa lista de contatos
    private var jsonArray: JSONArray? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // geramos uma lista de contatos com n elementos determinado pelo valor do argumento
        val listaContato = gerarContatos(5)

        binding.button.setOnClickListener {
            jsonArray = gerarJSONArray(listaContato)
            binding.textView2.text = jsonArray.toString()
        }

        binding.button2.setOnClickListener {
            val lista = recuperarListaContato()
            var stringExibicao = ""
            for (i in lista.indices) {
                stringExibicao = "${stringExibicao}\n${lista[i].toString()}"
            }
            binding.textView2.text = stringExibicao
        }
    }

    /**
     * Método que gera uma lista de contatos "pseudo-randomicamente"
     * O tamanho da lista é determinado pelo valor do parametro
     */
    fun gerarContatos(quantidade: Int): MutableList<Contato> {
        val lista = mutableListOf<Contato>()
        for (i in 1..quantidade) {
            var contatoGerado = Contato(
                idContato = i.toLong(),
                nome = "Contato ${i}",
                idade = i * 2
            )
            lista.add(contatoGerado)
        }
        return lista
    }

    /**
     * Método que recebe uma lista de contato e transforma em um JSON Array
     */
    fun gerarJSONArray(lista: MutableList<Contato>): JSONArray {
        val jsonArray = JSONArray()
        lista.forEach {
            jsonArray.put(it.gerarJSON())
        }
        return jsonArray
    }

    /**
     * Método que recebe um JSON Object e retorna um objeto do tipo Contato
     */
    fun recuperarContato(json: JSONObject): Contato {
        // recuperamos as informações de um JSON através do método JSONObject.get("chave")
        val idContato = json.get("idContato").toString().toLong()
        val nome = json.get("nome").toString()
        val idade = json.get("idade").toString().toInt()
        return Contato(idContato, nome , idade)
    }

    /**
     * Método que pega o valor da variavél global jsonArray
     * e recupera a lista de contatos do JSON
     */
    fun recuperarListaContato(): MutableList<Contato> {
        val lista = mutableListOf<Contato>()

        if (jsonArray != null) {
            for (i in 0 until jsonArray!!.length()) {
                lista.add(recuperarContato(jsonArray!![i] as JSONObject))
            }
        }

        return lista
    }
}