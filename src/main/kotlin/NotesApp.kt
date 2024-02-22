import java.util.Scanner
import kotlin.system.exitProcess

class NotesApp {
    private val archives: MutableList<Archive> = mutableListOf()
    private var currentArchiveIndex: Int = -1
    private var currentNoteIndex: Int = -1
    private val scanner = Scanner(System.`in`)

    fun start() {
        do {
            showArchiveMenu()
        } while (true)
    }

    private fun showArchiveMenu() {
        println("Список архивов:")
        archives.forEachIndexed { index, archive ->
            println("$index. ${archive.name}")
        }
        println("${archives.size}. Создать архив")
        println("${archives.size + 1}. Выход")

        when (val userInput = getUserInput()) {
            archives.size -> createArchive()
            archives.size + 1 -> exit()
            in 0 until archives.size -> {
                currentArchiveIndex = userInput
                showNoteMenu()
            }
            else -> {
                println("Некорректный ввод. Пожалуйста, выберите пункт из списка.")
            }
        }
    }

    private fun createArchive() {
        print("Введите имя нового архива: ")
        var archivename = ""
        while (archivename.isBlank()) {
            archivename = scanner.nextLine()
            if (archivename.isBlank()) {
                println("Имя не может быть пустым. Попробуйте еще раз.")
            }
        }
        val archive = Archive(archivename)
        archives.add(archive)
        currentArchiveIndex = archives.lastIndex
        showNoteMenu()
    }

    private fun showNoteMenu() {
        println("Список заметок в архиве '${archives[currentArchiveIndex].name}':")
        archives[currentArchiveIndex].notes.forEachIndexed { index, note ->
            println("$index. Заметка ${index + 1}")
        }
        println("${archives[currentArchiveIndex].notes.size}. Создать заметку")
        println("${archives[currentArchiveIndex].notes.size + 1}. Выход")

        when (val userInput = getUserInput()) {
            archives[currentArchiveIndex].notes.size -> createNote()
            archives[currentArchiveIndex].notes.size + 1 -> showArchiveMenu()
            in 0 until archives[currentArchiveIndex].notes.size -> {
                currentNoteIndex = userInput
                showNoteContent()
            }
            else -> {
                println("Некорректный ввод. Пожалуйста, выберите пункт из списка.")
            }
        }
    }

    private fun createNote() {
        print("введите текст новой заметки: ")
        val noteContent = readLine().orEmpty().trim()
        if (noteContent.isNotBlank()) {
            val note = Note(noteContent)
            archives[currentArchiveIndex].notes.add(note)
            currentNoteIndex = archives[currentArchiveIndex].notes.lastIndex
            showNoteContent()
        } else {
            println("Текст заметки не может быть пустым. Пожалуйста, попробуйте еще раз.")
            showNoteMenu()
        }
    }


    private fun showNoteContent() {
        println("Текст заметки ${currentNoteIndex + 1}:")
        val content = archives[currentArchiveIndex].notes[currentNoteIndex].content
        if (content.isNotEmpty()) {
            println(content)
            println("------------")
            println("0. Назад")
        }
        val userInput = getUserInput()
        when (userInput) {
            0 -> showNoteMenu()
            else -> {
                println("Некорректный ввод. Пожалуйста, выберите пункт из списка.")
            }
        }
    }


    private fun getUserInput(): Int {
        print("выберите пункт: ")
        while (true) {
            try {
                val userInput = readLine()?.toIntOrNull() ?: continue
                return userInput
            } catch (ex: NumberFormatException) {
                println("некорректный ввод. пожалуйста, выберите пункт из списка.")
            }
        }
    }

    private fun exit() {
        currentArchiveIndex = -1
        currentNoteIndex = -1
        exitProcess(0)
    }
}