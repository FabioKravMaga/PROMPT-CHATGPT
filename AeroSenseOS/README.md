# AeroSense OS (MVP)

Projeto Android em Kotlin para controle espacial híbrido com três motores:

1. **VisionEngine** (MediaPipe Hand Landmarker): detecta pinça (polegar + indicador) e converte em clique virtual.
2. **KineticEngine** (IMU / aceleração linear): detecta flick no pulso para ações globais de sistema.
3. **AeroSenseService** (AccessibilityService): injeta cliques/swipes e gera feedback háptico.

## Estrutura principal

- `app/src/main/java/com/aerosense/os/engine/VisionEngine.kt`
- `app/src/main/java/com/aerosense/os/engine/KineticEngine.kt`
- `app/src/main/java/com/aerosense/os/service/AeroSenseService.kt`

## Como executar

1. Abra a pasta `AeroSenseOS` no Android Studio (Hedgehog ou superior).
2. Sincronize Gradle.
3. Conceda permissão de câmera.
4. Ative o serviço em **Configurações > Acessibilidade > AeroSense Service**.

## Próximas evoluções

- Integrar pipeline CameraX + MediaPipe em tempo real.
- Adicionar motor de proximidade para modo "ocupado".
- Adicionar orquestrador de modos para alternar câmera/IMU conforme estado da tela.
