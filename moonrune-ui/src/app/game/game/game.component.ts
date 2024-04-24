import { NgIf } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { GameSettingsComponent } from '../game-settings/game-settings.component';
import { MultipleChoiceComponent } from '../multiple-choice/multiple-choice.component';
import { GameService } from '../game.service';


@Component({
  selector: 'app-game',
  standalone: true,
  imports: [NgIf, GameSettingsComponent, MultipleChoiceComponent],
  templateUrl: './game.component.html',
  styleUrl: './game.component.css'
})
export class GameComponent {


  constructor(private gameService: GameService) {}

  hasStarted = false;
  hasEnded = false;
  sessionID: number = 0;
  isValid: boolean = false;
  collectionName: string = "a";
  questionType: string = ""
  answerType: string = ""
  EMPTY = -1

  
  ngOnInit(): void {
    this.hasStarted = this.setToSettings();
  }

  setToSettings(): boolean {
    this.gameService.endGame(this.EMPTY).subscribe(hasStarted => this.hasStarted = hasStarted);
    return this.hasStarted
  }
  
  initializeGame(): void {
    this.startGame()
  }

  startGame() {
    this.gameService.startGame(10, 4).subscribe(id => {
      this.sessionID = id
      this.setQuestion()
    });
  }

  setQuestion() {
    this.gameService.setQuestion(this.sessionID, this.questionType, this.answerType).subscribe(valid => {
      this.isValid = valid
      if (!this.isValid) return;
      this.setTerms()
    })
  }

  setTerms() {
    this.gameService.setTerms(this.sessionID, this.collectionName).subscribe(valid => this.hasStarted = valid)
  }

  endGame(): void {
    this.gameService.endGame(this.sessionID).subscribe(hasStarted => this.hasStarted = hasStarted);
  }
}
