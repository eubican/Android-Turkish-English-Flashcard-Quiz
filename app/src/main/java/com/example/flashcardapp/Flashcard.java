package com.example.flashcardapp;

public class Flashcard {
    public int flashcardID;
    public String flashcardTR;
    public String FlashcardEN;

    public Flashcard(){}

    public Flashcard(int flashcardID, String flashcardTR, String flashcardEN) {
        this.flashcardID = flashcardID;
        this.flashcardTR = flashcardTR;
        FlashcardEN = flashcardEN;
    }

    public Flashcard(String flashcardTR, String flashcardEN) {
        this.flashcardTR = flashcardTR;
        FlashcardEN = flashcardEN;
    }

    public int getFlashcardID() {
        return flashcardID;
    }

    public String getFlashcardTR() {
        return flashcardTR;
    }

    public String getFlashcardEN() {
        return FlashcardEN;
    }

    public void setFlashcardID(int flashcardID) {
        this.flashcardID = flashcardID;
    }

    public void setFlashcardTR(String flashcardTR) {
        this.flashcardTR = flashcardTR;
    }

    public void setFlashcardEN(String flashcardEN) {
        FlashcardEN = flashcardEN;
    }

}
