# K-Dilebo Trader Android App — v1.0 Prototype

This is a Kotlin + Jetpack Compose Android dashboard for controlling/monitoring the K-Dilebo TrendBot.

## Current version
The UI is a prototype. It does NOT yet connect to a live MT5 account and does NOT execute trades.

## Intended architecture
Android app
    -> secure HTTPS API
    -> VPS/backend
    -> MT5 desktop
    -> K-Dilebo TrendBot EA
    -> broker

## Build
Open this folder in Android Studio and let Gradle sync. Then run the app on an Android device/emulator.

## Safety
Do not connect a real-money account until the EA and API have been independently tested. The emergency-close control must require authentication and server-side confirmation before production use.
