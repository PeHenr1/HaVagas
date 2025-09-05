package br.edu.ifsp.scl.ads.prdm.sc3039048.havagas

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var checkBoxCelular: CheckBox
    private lateinit var editTextCelular: EditText
    private lateinit var spinnerFormacao: Spinner
    private lateinit var editTextAno: EditText
    private lateinit var editTextInstituicao: EditText
    private lateinit var editTextTitulo: EditText
    private lateinit var editTextOrientador: EditText
    private lateinit var spinnerSexo: Spinner
    private lateinit var editTextSexoOutro: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkBoxCelular = findViewById(R.id.checkBoxAdicionarCelular)
        editTextCelular = findViewById(R.id.editTextCelular)
        spinnerFormacao = findViewById(R.id.spinnerFormacao)
        editTextAno = findViewById(R.id.editTextAnoConclusao)
        editTextInstituicao = findViewById(R.id.editTextInstituicao)
        editTextTitulo = findViewById(R.id.editTextTituloMonografia)
        editTextOrientador = findViewById(R.id.editTextOrientador)
        spinnerSexo = findViewById(R.id.spinnerSexo)
        editTextSexoOutro = findViewById(R.id.editTextSexoOutro)

        editTextCelular.visibility = View.GONE
        editTextAno.visibility = View.GONE
        editTextInstituicao.visibility = View.GONE
        editTextTitulo.visibility = View.GONE
        editTextOrientador.visibility = View.GONE
        editTextSexoOutro.visibility = View.GONE

        checkBoxCelular.setOnCheckedChangeListener { _, isChecked ->
            editTextCelular.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        spinnerFormacao.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                editTextAno.visibility = View.GONE
                editTextInstituicao.visibility = View.GONE
                editTextTitulo.visibility = View.GONE
                editTextOrientador.visibility = View.GONE

                when (position) {
                    0, 1 -> { // Fundamental e Médio
                        editTextAno.visibility = View.VISIBLE
                        editTextInstituicao.visibility = View.GONE
                        editTextTitulo.visibility = View.GONE
                        editTextOrientador.visibility = View.GONE
                    }
                    2, 3 -> { // Graduação e Especialização
                        editTextAno.visibility = View.VISIBLE
                        editTextInstituicao.visibility = View.VISIBLE
                        editTextTitulo.visibility = View.GONE
                        editTextOrientador.visibility = View.GONE
                    }
                    4, 5 -> { // Mestrado e Doutorado
                        editTextAno.visibility = View.VISIBLE
                        editTextInstituicao.visibility = View.VISIBLE
                        editTextTitulo.visibility = View.VISIBLE
                        editTextOrientador.visibility = View.VISIBLE
                    }
                    else -> {
                        editTextAno.visibility = View.GONE
                        editTextInstituicao.visibility = View.GONE
                        editTextTitulo.visibility = View.GONE
                        editTextOrientador.visibility = View.GONE
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                editTextAno.visibility = View.GONE
                editTextInstituicao.visibility = View.GONE
                editTextTitulo.visibility = View.GONE
                editTextOrientador.visibility = View.GONE
            }
        }

        spinnerSexo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                editTextSexoOutro.visibility = if (position == parent.count - 1) View.VISIBLE else View.GONE
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                editTextSexoOutro.visibility = View.GONE
            }
        }

        findViewById<Button>(R.id.buttonSalvar).setOnClickListener {
            Toast.makeText(this, "Formulário salvo!", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.buttonLimpar).setOnClickListener {
            limparFormulario()
        }
    }

    private fun mostrarFormulario() {
        val nome = findViewById<EditText>(R.id.editTextNome).text.toString()
        val email = findViewById<EditText>(R.id.editTextEmail).text.toString()
        val telefone = findViewById<EditText>(R.id.editTextTelefone).text.toString()
        val celular = editTextCelular.text.toString()
        val dataNascimento = findViewById<EditText>(R.id.editTextDataNascimento).text.toString()
        val formacao = spinnerFormacao.selectedItem.toString()
        val ano = editTextAno.text.toString()
        val instituicao = editTextInstituicao.text.toString()
        val titulo = editTextTitulo.text.toString()
        val orientador = editTextOrientador.text.toString()
        val sexo = spinnerSexo.selectedItem.toString()
        val sexoOutro = editTextSexoOutro.text.toString()
        val vagas = findViewById<EditText>(R.id.editTextVagasInteresse).text.toString()

        val mensagem = StringBuilder()
        mensagem.append("Nome: $nome\n")
        mensagem.append("E-mail: $email\n")
        mensagem.append("Telefone: $telefone\n")
        if (checkBoxCelular.isChecked) mensagem.append("Celular: $celular\n")
        mensagem.append("Data de Nascimento: $dataNascimento\n")
        mensagem.append("Sexo: ${if (sexo == "Outro") sexoOutro else sexo}\n")
        mensagem.append("Formação Acadêmica: $formacao\n")
        if (editTextAno.visibility == View.VISIBLE) mensagem.append("Ano de Conclusão: $ano\n")
        if (editTextInstituicao.visibility == View.VISIBLE) mensagem.append("Instituição: $instituicao\n")
        if (editTextTitulo.visibility == View.VISIBLE) mensagem.append("Título Monografia: $titulo\n")
        if (editTextOrientador.visibility == View.VISIBLE) mensagem.append("Orientador: $orientador\n")
        mensagem.append("Vagas de Interesse: $vagas\n")

        AlertDialog.Builder(this)
            .setTitle("Formulário Preenchido")
            .setMessage(mensagem.toString())
            .setPositiveButton("OK", null)
            .show()
    }

    private fun limparFormulario() {
        val editTexts = listOf<EditText>(
            findViewById(R.id.editTextNome),
            findViewById(R.id.editTextEmail),
            findViewById(R.id.editTextTelefone),
            editTextCelular,
            findViewById(R.id.editTextDataNascimento),
            editTextAno,
            editTextInstituicao,
            editTextTitulo,
            editTextOrientador,
            editTextSexoOutro,
            findViewById(R.id.editTextVagasInteresse)
        )

        editTexts.forEach { it.text.clear() }

        checkBoxCelular.isChecked = false
        findViewById<CheckBox>(R.id.checkBoxReceberEmails).isChecked = false

        spinnerSexo.setSelection(0)
        spinnerFormacao.setSelection(0)

        editTextAno.visibility = View.GONE
        editTextInstituicao.visibility = View.GONE
        editTextTitulo.visibility = View.GONE
        editTextOrientador.visibility = View.GONE
        editTextSexoOutro.visibility = View.GONE
    }
}
