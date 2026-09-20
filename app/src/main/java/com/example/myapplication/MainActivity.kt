package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TicketBookingScreen()
                }
            }
        }
    }
}

// Fungsi helper untuk merubah angka biasa (25000) menjadi format Rupiah (Rp25.000)
fun formatRupiah(amount: Int): String {
    val localeID = Locale.Builder().setLanguage("in").setRegion("ID").build()
    val formatter = NumberFormat.getNumberInstance(localeID)
    return "Rp${formatter.format(amount)}"
}

@Composable
fun TicketBookingScreen() {
    // 1. HARGA TIKET (Nilai statis / konstan)
    val hargaTiket = 25000

    // 2. STATE JUMLAH TIKET (Nilai dinamis yang dapat berubah saat tombol diklik)
    var jumlahTiket by remember { mutableIntStateOf(1) }

    // 3. TOTAL BAYAR (Dihitung otomatis tiap kali jumlahTiket berubah karena recomposition)
    val totalBayar = jumlahTiket * hargaTiket

    // Palet Warna sesuai Mockup
    val primaryBlue = Color(0xFF1E88E5)
    val cardBgColor = Color(0xFFF7F9FC)
    val greenText = Color(0xFF1B8A44)
    val redReset = Color(0xFFE53935)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryBlue)
    ) {
        // --- HEADER BIRU ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, bottom = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.ConfirmationNumber,
                contentDescription = "Ikon Tiket",
                tint = Color.White,
                modifier = Modifier.size(56.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Pemesanan Tiket",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Pesan tiket dengan mudah!",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 14.sp
            )
        }

        // --- BODY KONTEN (Layar Putih Bawah) ---
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                // KARTU 1: HARGA TIKET
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = cardBgColor),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "Harga Tiket",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = formatRupiah(hargaTiket),
                            color = primaryBlue,
                            fontWeight = FontWeight.Bold,
                            fontSize = 26.sp
                        )
                        Text(
                            text = "per tiket",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }
                }

                // KARTU 2: JUMLAH TIKET (Dapat diubah dengan tombol - dan +)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = cardBgColor),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "Jumlah Tiket",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Tombol Minus (-)
                            IconButton(
                                onClick = {
                                    if (jumlahTiket > 1) {
                                        jumlahTiket--
                                    }
                                },
                                modifier = Modifier
                                    .size(46.dp)
                                    .background(primaryBlue, CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = "Kurang",
                                    tint = Color.White
                                )
                            }

                            // Teks Jumlah Tiket saat ini
                            Text(
                                text = "$jumlahTiket",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )

                            // Tombol Plus (+)
                            IconButton(
                                onClick = {
                                    jumlahTiket++
                                },
                                modifier = Modifier
                                    .size(46.dp)
                                    .background(primaryBlue, CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Tambah",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }

                // KARTU 3: TOTAL BAYAR (Otomatis berubah)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = cardBgColor),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "Total",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = formatRupiah(totalBayar),
                            color = greenText,
                            fontWeight = FontWeight.Bold,
                            fontSize = 26.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // TOMBOL RESET (Merah)
                Button(
                    onClick = {
                        jumlahTiket = 1 // Reset state ke kondisi semula
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = redReset),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reset",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "RESET",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTicketScreen() {
    MaterialTheme {
        TicketBookingScreen()
    }
}