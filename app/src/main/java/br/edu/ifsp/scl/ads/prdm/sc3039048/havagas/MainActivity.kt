package br.edu.ifsp.scl.ads.prdm.sc3039048.havagas

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

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
                    0, 1 -> {
                        editTextAno.visibility = View.VISIBLE
                    }
                    2, 3 -> {
                        editTextAno.visibility = View.VISIBLE
                        editTextInstituicao.visibility = View.VISIBLE
                    }
                    4, 5 -> {
                        editTextAno.visibility = View.VISIBLE
                        editTextInstituicao.visibility = View.VISIBLE
                        editTextTitulo.visibility = View.VISIBLE
                        editTextOrientador.visibility = View.VISIBLE
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
            if (validarFormulario()) mostrarFormulario()
        }

        findViewById<Button>(R.id.buttonLimpar).setOnClickListener {
            limparFormulario()
        }
    }

    private fun validarFormulario(): Boolean {
        val nome = findViewById<EditText>(R.id.editTextNome).text.toString().trim()
        val email = findViewById<EditText>(R.id.editTextEmail).text.toString().trim()
        val telefone = findViewById<EditText>(R.id.editTextTelefone).text.toString().trim()
        val celular = editTextCelular.text.toString().trim()
        val dataNascimento = findViewById<EditText>(R.id.editTextDataNascimento).text.toString().trim()
        val ano = editTextAno.text.toString().trim()

        if (nome.isEmpty()) {
            showToast("Preencha o nome corretamente.")
            return false
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Preencha um e-mail válido.")
            return false
        }
        if (telefone.isEmpty() || !telefone.matches("\\d{8,11}".toRegex())) {
            showToast("Preencha um telefone válido (8 a 11 dígitos).")
            return false
        }
        if (checkBoxCelular.isChecked && (celular.isEmpty() || !celular.matches("\\d{8,11}".toRegex()))) {
            showToast("Preencha um celular válido (8 a 11 dígitos).")
            return false
        }
        if (dataNascimento.isEmpty()) {
            showToast("Preencha a data de nascimento.")
            return false
        }
        if (editTextAno.isVisible && (ano.isEmpty() || !ano.matches("\\d{4}".toRegex()))) {
            showToast("Preencha o ano de conclusão corretamente (4 dígitos).")
            return false
        }
        return true
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
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
        if (editTextAno.isVisible) mensagem.append("Ano de Conclusão: $ano\n")
        if (editTextInstituicao.isVisible) mensagem.append("Instituição: $instituicao\n")
        if (editTextTitulo.isVisible) mensagem.append("Título Monografia: $titulo\n")
        if (editTextOrientador.isVisible) mensagem.append("Orientador: $orientador\n")
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
