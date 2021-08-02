package ru.netology

import kotlin.math.min


val names: MutableList<Name> = mutableListOf(
    Name(1, "Ivan Ivanov"),
    Name(2, "Petr Petrov"),
    Name(3, "Sidr Sidorov"),
    Name(4, "Mikhail Mikhailov"),
    Name(5, "Elvis Presley")
)

fun main() {
    val chatService = ChatService()

    println(chatService.addMessages(1, true, "First comment"))
    println(chatService.addMessages(1, false, "Negative comment!"))
    println(chatService.addMessages(2, true, "Second comment"))
    println("\n Talking with your friend ${chatService.getName(1)} :\n ${chatService.getMessage(1, 0, 100)}")

    println("\n ${chatService.getChats(false)}")
    println("\n ${chatService.getChats(true)}")

    println("\n ${chatService.deleteMessage(1,1)}")
    println("\n Chat with a friend ${chatService.getName(1)} :\n ${chatService.getMessage(1,0,100)}")

    println("\n ${chatService.deleteChat(1)}")
    println("\n Chat with a friend ${chatService.getName(1)} :\n ${chatService.getMessage(1,0,100)}")


}

//собеседники
class Name(val id: Int, val name: String) {}

class Message(
    var id: Int,
    val myReply: Boolean,
    val messageText: String,
    val readStatus: Boolean,
    var deleteStatus: Boolean
)

class Chat(val id: Int, var messages: MutableList<Message>) {

    fun add(message: Message): Int {
        message.id = this.messages.size
        this.messages.add(message)
        return this.messages.last().id
    }


    fun get(id: Int, count: Int): String {
        val size = this.messages.size
        if (id >= size) return ""
        val idLast = min((id + count), size) //размер диапазона <= размеру
        val messages = this.messages.subList(id, idLast)
        var returnValue = ""
        for (message in messages) {
            if (!message.deleteStatus) returnValue += ((if (message.myReply) "Me: " else "Interlocutor: ") + "${message.messageText} \n")
        }
        return returnValue
    }
}

class ChatService() {
    var chats: MutableList<Chat> = mutableListOf()

    fun deleteChat(chatId: Int?): String {
        if (chatId == null) return "No chat id selected"
        return if (chats.removeIf { it.id == chatId }) "Chat with ${getName(chatId)} successfully deleted"
        else
            "Chat not found ${getName(chatId)}"
    }

    fun getName(nameId: Int): String? {
        return names.findLast { it.id == nameId }?.name
    }

    fun findChat(chatId: Int?): Chat? {
        return chats.findLast { it.id == chatId }
    }

    fun addMessages(id: Int?, myReply: Boolean, messageText: String): String {
        if (id == null) return "Chat Id don't matched"
        var chat = findChat(id)
        if (chat == null) {
            chat = Chat(id, ArrayList())
            chats.add(chat)
        }
        val messageId = chat.add(Message(0, myReply, messageText, myReply, false))
        return "Chat username: ${getName(id)} add a message with Id ${messageId}"
    }

    fun deleteMessage(id: Int?, messageId: Int?): String {
        if (id == null) return "Chat Id don't matched"
        if (messageId == null) return "Message Id don't matched"
        val chat = findChat(id)
        if (chat == null) return "Can't find a chat with Id = $id"
        if (!chat.messages.removeIf { it.id == messageId }) return "Don't find a message Id = $messageId" else {
            if (chat.messages.isEmpty()) chats.removeIf { it.id == id }
            return "Message ${getName(id)} with an Id number $messageId successfully deleted!"
        }
    }

    fun getMessage(chatId: Int?, messageId: Int?, count: Int?): String {
        if (chatId == null || messageId == null || count == null) return "Wrong parameters!"
        val chat = findChat(chatId)
        if (chat == null) return "Chat $chatId not existed!"
        return chat.get(messageId, count)
    }

    fun getUnreadChats(): String {
        val chats: List<Chat> = chats.filter { it.messages.filter { !it.readStatus }.isNotEmpty() }
        if (chats.isEmpty()) return "No have unread messages!"
        return ("Chats with unread messages: \n" + getListChats(chats))

    }

    fun getChats(readStatus: Boolean): String {
        if (!readStatus) return getUnreadChats()
        val chats: List<Chat> = chats.filter { it.messages.isNotEmpty() }
        return ("All chats list: \n" + getListChats(chats))
    }

    fun getListChats(chats: List<Chat>): String {
        var returnValue = ""
        for (chat in chats) returnValue += "${getName(chat.id)} \n"
        returnValue += "Number of chats ${chats.count()}"
        return returnValue
    }


}



