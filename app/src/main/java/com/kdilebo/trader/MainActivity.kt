package com.kdilebo.trader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight

data class Trade(val symbol: String, val side: String, val result: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { KDileboApp() }
    }
}

@Composable
fun KDileboApp() {
    var botEnabled by remember { mutableStateOf(false) }
    var risk by remember { mutableFloatStateOf(0.5f) }
    var dailyLoss by remember { mutableFloatStateOf(2f) }

    val trades = listOf(
        Trade("NAS100", "BUY", "+$12.40"),
        Trade("XAUUSD", "SELL", "-$4.80"),
        Trade("EURUSD", "BUY", "+$7.15")
    )

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Column {
                        Text("K-Dilebo Trader", fontWeight = FontWeight.Bold)
                        Text("MT5 Bot Control", style = MaterialTheme.typography.labelSmall)
                    }
                })
            }
        ) { pad ->
            LazyColumn(
                modifier = Modifier.padding(pad).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(18.dp)) {
                            Text("Bot status", style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(8.dp))
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(if (botEnabled) "RUNNING" else "STOPPED",
                                    fontWeight = FontWeight.Bold)
                                Switch(
                                    checked = botEnabled,
                                    onCheckedChange = { botEnabled = it }
                                )
                            }
                            Text(
                                if (botEnabled) "Execution remains on your MT5 VPS."
                                else "Bot is currently disabled.",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }

                item {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        MetricCard("Equity", "$1,000.00", Modifier.weight(1f))
                        MetricCard("Today", "+$14.75", Modifier.weight(1f))
                    }
                }

                item {
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(18.dp)) {
                            Text("Risk controls", style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(8.dp))
                            Text("Risk per trade: ${"%.2f".format(risk)}%")
                            Slider(value = risk, onValueChange = { risk = it },
                                valueRange = 0.1f..2f, steps = 18)
                            Text("Daily loss limit: ${"%.1f".format(dailyLoss)}%")
                            Slider(value = dailyLoss, onValueChange = { dailyLoss = it },
                                valueRange = 1f..5f, steps = 7)
                            Text("Suggested starting values: 0.5% risk / 2% daily limit",
                                style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }

                item {
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(18.dp)) {
                            Text("Markets", style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(8.dp))
                            listOf("NAS100", "XAUUSD", "EURUSD", "GBPUSD", "USDJPY").forEach {
                                Row(
                                    Modifier.fillMaxWidth().padding(vertical = 5.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(it)
                                    Text("Monitoring")
                                }
                            }
                        }
                    }
                }

                item {
                    Text("Recent trades", style = MaterialTheme.typography.titleMedium)
                }

                items(trades) { t ->
                    Card(Modifier.fillMaxWidth()) {
                        Row(
                            Modifier.fillMaxWidth().padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(t.symbol, fontWeight = FontWeight.Bold)
                                Text(t.side, style = MaterialTheme.typography.labelSmall)
                            }
                            Text(t.result, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                item {
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("EMERGENCY: CLOSE EA TRADES")
                    }
                }

                item {
                    Text(
                        "Prototype dashboard: live values and controls are placeholders until a secure MT5/VPS API is connected.",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
fun MetricCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier) {
        Column(Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.labelMedium)
            Spacer(Modifier.height(4.dp))
            Text(value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }
    }
}
