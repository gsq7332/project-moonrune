import { Component, Input, Output } from '@angular/core';
import { GameService } from '../game.service';
import { EventEmitter } from '@angular/core';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-game-settings',
  standalone: true,
  imports: [NgIf],
  templateUrl: './game-settings.component.html',
  styleUrl: './game-settings.component.css'
})
export class GameSettingsComponent {
  @Input() collection ?: string;
  @Input() numQuestions: number = 10
  @Input() numAnswers: number = 4
  @Input() isDiacritic ?: boolean;
  @Input() questionType ?: string;
  @Input() answerType ?: string;
  @Output() questionTypeChange = new EventEmitter<string>()
  @Output() answerTypeChange = new EventEmitter<string>()

  @Output() startGameEvent = new EventEmitter();
  @Input() currentLevel ?: number;
  /*
  level representations:
  0 - Default/Regular (used for user generated terms + Hangul)
  1 - Kanas (added Diacritics)
  2 - Cyrillic (added lowercase)
  3 - Greek (lowercase + name)
  4 - Kanji (readings)
  */

  constructor(private gameService: GameService) {}

  ngOnInit() {

  }

  changeQuestionType() {
    if (this.questionType == undefined) return
    this.questionTypeChange.emit(this.questionType)
  }

  changeAnswerType() {
    if (this.answerType == undefined) return
    this.answerTypeChange.emit(this.answerType)
  }

  startGame() {
    
    this.startGameEvent.emit("Starting game");
    //this.gameService.startGame();
  }
}