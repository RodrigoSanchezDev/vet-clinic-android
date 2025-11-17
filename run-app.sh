#!/bin/bash

# Script para ejecutar la app Vet Clinic en el emulador Pixel 4
# Desarrollado por: Rodrigo Sánchez

echo "🚀 Iniciando app Vet Clinic Android..."
echo "================================================"

# Colores para output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Directorio del proyecto
PROJECT_DIR="$HOME/Library/Mobile Documents/com~apple~CloudDocs/DUOC/DESARROLLO APP MOVILES I_001A/Semana 4/vet-clinic-android"
cd "$PROJECT_DIR" || exit 1

echo -e "${YELLOW}📂 Directorio del proyecto: ${NC}$PROJECT_DIR"

# Buscar Android SDK
echo -e "\n${YELLOW}🔍 Buscando Android SDK...${NC}"

if [ -d "$HOME/Library/Android/sdk" ]; then
    ANDROID_SDK="$HOME/Library/Android/sdk"
elif [ -d "$HOME/Android/Sdk" ]; then
    ANDROID_SDK="$HOME/Android/Sdk"
elif [ -n "$ANDROID_HOME" ]; then
    ANDROID_SDK="$ANDROID_HOME"
else
    echo -e "${RED}❌ Android SDK no encontrado${NC}"
    echo "Por favor, instala Android Studio primero"
    exit 1
fi

echo -e "${GREEN}✅ Android SDK encontrado: ${NC}$ANDROID_SDK"

# Configurar variables de entorno
export ANDROID_HOME="$ANDROID_SDK"
export PATH="$PATH:$ANDROID_SDK/emulator:$ANDROID_SDK/platform-tools"

# Verificar si existe adb
if ! command -v adb &> /dev/null; then
    if [ -f "$ANDROID_SDK/platform-tools/adb" ]; then
        ADB="$ANDROID_SDK/platform-tools/adb"
    else
        echo -e "${RED}❌ ADB no encontrado${NC}"
        exit 1
    fi
else
    ADB="adb"
fi

# Verificar si existe emulator
if ! command -v emulator &> /dev/null; then
    if [ -f "$ANDROID_SDK/emulator/emulator" ]; then
        EMULATOR="$ANDROID_SDK/emulator/emulator"
    else
        echo -e "${RED}❌ Emulator no encontrado${NC}"
        exit 1
    fi
else
    EMULATOR="emulator"
fi

# Listar dispositivos virtuales disponibles
echo -e "\n${YELLOW}📱 Dispositivos virtuales disponibles:${NC}"
AVD_LIST=$($EMULATOR -list-avds 2>/dev/null)

if [ -z "$AVD_LIST" ]; then
    echo -e "${RED}❌ No hay dispositivos virtuales configurados${NC}"
    echo "Crea uno en Android Studio: Tools → Device Manager → Create Device"
    exit 1
fi

echo "$AVD_LIST"

# Buscar Pixel 4
PIXEL_AVD=""
while IFS= read -r avd; do
    if [[ "$avd" == *"Pixel"*"4"* ]] || [[ "$avd" == *"Pixel_4"* ]]; then
        PIXEL_AVD="$avd"
        break
    fi
done <<< "$AVD_LIST"

if [ -z "$PIXEL_AVD" ]; then
    # Si no encuentra Pixel 4, usar el primero disponible
    PIXEL_AVD=$(echo "$AVD_LIST" | head -n 1)
    echo -e "${YELLOW}⚠️  Pixel 4 no encontrado, usando: ${NC}$PIXEL_AVD"
else
    echo -e "${GREEN}✅ Pixel 4 encontrado: ${NC}$PIXEL_AVD"
fi

# Verificar si ya hay un emulador corriendo
echo -e "\n${YELLOW}🔍 Verificando dispositivos conectados...${NC}"
DEVICES=$($ADB devices | grep -v "List" | grep "device$")

if [ -z "$DEVICES" ]; then
    echo -e "${YELLOW}📱 Iniciando emulador: ${NC}$PIXEL_AVD"
    echo "   Esto puede tomar 1-2 minutos..."

    # Iniciar emulador en background
    $EMULATOR -avd "$PIXEL_AVD" -no-snapshot-load > /dev/null 2>&1 &
    EMULATOR_PID=$!

    echo -e "${GREEN}✅ Emulador iniciado (PID: $EMULATOR_PID)${NC}"
    echo "   Esperando que el emulador esté listo..."

    # Esperar a que el emulador esté completamente iniciado
    $ADB wait-for-device

    # Esperar boot completo
    while [ "$($ADB shell getprop sys.boot_completed 2>/dev/null | tr -d '\r')" != "1" ]; do
        echo -n "."
        sleep 2
    done
    echo ""
    echo -e "${GREEN}✅ Emulador listo${NC}"
else
    echo -e "${GREEN}✅ Dispositivo ya conectado${NC}"
    $ADB devices
fi

# Compilar e instalar la app
echo -e "\n${YELLOW}🔨 Compilando la aplicación...${NC}"
./gradlew installDebug --console=plain 2>&1 | grep -E "(BUILD|Task|Installing|INSTALLED|SUCCESS|FAILED)"

if [ $? -eq 0 ]; then
    echo -e "\n${GREEN}✅ App instalada exitosamente${NC}"

    # Iniciar la app
    echo -e "\n${YELLOW}🚀 Iniciando la aplicación...${NC}"
    $ADB shell am start -n com.example.vet_clinic_android/.MainActivity

    echo -e "\n${GREEN}✅ ¡Listo! La app debería estar corriendo en el emulador${NC}"
    echo ""
    echo "================================================"
    echo "📱 App: Clínica Veterinaria"
    echo "👨‍💻 Desarrollador: Rodrigo Sánchez"
    echo "📧 rodrigo@sanchezdev.com"
    echo "================================================"
    echo ""
    echo "Para ver los logs en tiempo real:"
    echo "  $ADB logcat | grep VetClinic"
    echo ""
    echo "Para desinstalar:"
    echo "  $ADB uninstall com.example.vet_clinic_android"
    echo ""
else
    echo -e "\n${RED}❌ Error al compilar/instalar la app${NC}"
    echo "Ejecuta manualmente: ./gradlew installDebug"
    exit 1
fi

