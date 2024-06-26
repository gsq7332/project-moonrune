import { Component, Input, Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-game-settings',
  standalone: true,
  imports: [NgIf, FormsModule],
  templateUrl: './game-settings.component.html',
  styleUrl: './game-settings.component.css'
})
export class GameSettingsComponent {
  @Input() numQuestions ?: number;
  @Input() numAnswers ?: number;
  @Input() questionType ?: string;
  @Input() answerType ?: string;
  @Output() questionTypeChange = new EventEmitter<string>()
  @Output() answerTypeChange = new EventEmitter<string>()
  @Output() numQuestionsChange = new EventEmitter<number>()
  @Output() numAnswersChange = new EventEmitter<number>()

  @Output() startGameEvent = new EventEmitter();
  @Input() collectionID ?: number;
  /*
  level representations:
  0 - Default/Regular (used for user generated terms + Hangul)
  1 - Kanas (added Diacritics)
  2 - Cyrillic (added lowercase)
  3 - Greek (lowercase + name)
  4 - Kanji (readings)
  */

  constructor() {}

  ngOnInit() {

  }

  changeNumQuestions() {
    this.numQuestionsChange.emit(this.numQuestions)
  }

  changeNumAnswers() {
    this.numAnswersChange.emit(this.numAnswers)
  }

  changeQuestionType() {
    //if (this.questionType == undefined) return
    this.questionTypeChange.emit(this.questionType)
  }

  changeAnswerType() {
    //if (this.answerType == undefined) return
    this.answerTypeChange.emit(this.answerType)
  }

  startGame() {
    
    this.startGameEvent.emit("Starting game");
    //this.gameService.startGame();
  }
}