# PersonalTasks

PersonalTasks é um aplicativo Android para gerenciamento de tarefas, desenvolvido com Kotlin. Os usuários podem criar, editar, visualizar e excluir tarefas com data limite. O app utiliza Room para persistência local dos dados.

## Vídeo
Link para o vídeo de demonstração do aplicativo sendo executado:
[Clique aqui](https://youtu.be/otX0wWEeAFY)

## Funcionalidades

- Adicionar novas tarefas com título, descrição e data limite
- Editar tarefas existentes
- Visualizar tarefa em modo somente leitura
- Excluir tarefas
- Persistência local com Room

## Tecnologias utilizadas

- **Kotlin**
- **Room** (persistência local)
- **ViewBinding**
- **RecyclerView**
- **API mínima:** Android 8.0 (API 26)


## Como Rodar

1. Clone o projeto:
   ```bash
   git clone https://github.com/FelipeSantosLourenco/PersonalTasks

2. Abra no Android Studio.

3. Aguarde o Gradle sincronizar.

4. Conecte um emulador ou dispositivo Android.

5. Clique em Run.

## Gerar APK com Docker

### 1. Construir a Imagem Docker

Para construir a imagem Docker, use o seguinte comando:

```bash  
docker build -t android-apk-builder .
```

Isso criará uma imagem chamada `android-apk-builder` com base no `Dockerfile` presente neste repositório.

### 2. Rodar o Container e Gerar o APK
```bash  
docker run --rm -v "${PWD}:/output" android-apk-builder cp /personalTasks.apk /output/
```

### 3. Localização do APK Gerado
Após a execução bem-sucedida do comando, você encontrará o APK  `personalTasks.apk` pronto para ser usado ou instalado em um dispositivo Android.
