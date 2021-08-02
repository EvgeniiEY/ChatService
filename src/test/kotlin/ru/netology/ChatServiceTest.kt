package ru.netology

import junit.framework.Assert.assertEquals
import org.junit.Test
import java.lang.Character.getName


class ChatServiceTest {

    @Test
    fun chatAddChatTest() {
        val messageTest = Message(1, true, "TEST TEXT", false, false)
        var messages: MutableList<Message> = mutableListOf()
        val chatTest = Chat(1, messages)
        val result = chatTest.add(messageTest)
        assertEquals(0, result)
    }

    @Test
    fun chatGetNameTest() {
        var messages: MutableList<Message> = mutableListOf()
        val chatTest = Chat(1, messages)
        val result = chatTest.get(1, 1)
        assertEquals("", result)
    }

    @Test
    fun ChatServiceDeleteChatTest() {
        val chatServiceTest = ChatService()
        val result = chatServiceTest.deleteChat(1)
        assertEquals("Chat not found Ivan Ivanov", result)

    }

    @Test
    fun ChatServiceGetNameTest() {
        val nameId = 1
        val chatServiceTest = ChatService()
        val result = chatServiceTest.getName(nameId)
        assertEquals("Ivan Ivanov", result)
    }

    @Test
    fun ChatServiceFindChatTest() {
        val chatId = 1
        val chatServiceTest = ChatService()
        val result = chatServiceTest.findChat(chatId)
        assertEquals(null, result)
    }

    @Test
    fun ChatServiceAddMessages() {
        val id = 1
        val myReply = true
        val messageText = "Text"
        val deleteStatus = false
        val chatServiceTest = ChatService()
        val result = chatServiceTest.addMessages(id,myReply,messageText)
        assertEquals("Chat username: Ivan Ivanov add a message with Id 0", result)
    }



}

