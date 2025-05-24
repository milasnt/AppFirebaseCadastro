package com.example.appaulafirebase

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appaulafirebase.ui.theme.AppAulaFirebaseTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppAulaFirebaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    appAula()
                }
            }
        }
    }
}

@Composable
fun appAula() {
    var nome by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var idade by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }

    var dadosCadastrados by remember { mutableStateOf("") }

    val darkPink = Color(0xFF943680)

    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            Arrangement.Center
        ) {
            Text(
                "Cadastro",
                fontFamily = FontFamily.Default,
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = darkPink
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            Arrangement.Center
        ) {
            TextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome") }
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            Arrangement.Center
        ) {
            TextField(
                value = genero,
                onValueChange = { genero = it },
                label = { Text("Gênero") }
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            Arrangement.Center
        ) {
            TextField(
                value = idade,
                onValueChange = { idade = it },
                label = { Text("Idade") }
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            Arrangement.Center
        ) {
            TextField(
                value = endereco,
                onValueChange = { endereco = it },
                label = { Text("Endereço") }
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            Arrangement.Center
        ) {
            TextField(
                value = cpf,
                onValueChange = { cpf = it },
                label = { Text("CPF") }
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            Arrangement.Center
        ) {
            Button(
                onClick = {
                    val db = Firebase.firestore
                    val user = hashMapOf(
                        "nome" to nome,
                        "genero" to genero,
                        "idade" to idade,
                        "endereco" to endereco,
                        "cpf" to cpf
                    )

                    db.collection("users")
                        .add(user)
                        .addOnSuccessListener { documentReference ->
                            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                        }

                    dadosCadastrados = """
                        Nome: $nome
                        Gênero: $genero
                        Idade: $idade
                        Endereço: $endereco
                        CPF: $cpf
                    """.trimIndent()

                    // Limpar os campos
                    nome = ""
                    genero = ""
                    idade = ""
                    endereco = ""
                    cpf = ""
                },
                colors = ButtonDefaults.buttonColors(containerColor = darkPink)
            ) {
                Text("Cadastrar", fontSize = 20.sp, color = Color.White)
            }
        }

        if (dadosCadastrados.isNotEmpty()) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                Arrangement.Center
            ) {
                Text(
                    text = "Último Cadastro:\n$dadosCadastrados",
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun AppAulaFirebasePreview() {
    AppAulaFirebaseTheme {
            appAula()
    }
}