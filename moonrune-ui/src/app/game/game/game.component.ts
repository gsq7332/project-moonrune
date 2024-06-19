import { NgIf } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { GameSettingsComponent } from '../game-settings/game-settings.component';
import { MultipleChoiceComponent } from '../multiple-choice/multiple-choice.component';
import { GameService } from '../game.service';
import { ActivatedRoute, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { MainRoutingComponent } from '../../general/main-routing/main-routing.component';
import { GameProperties } from '../game-properties';
import { Game } from '../game';
import { filters } from '../../terms/filters';


@Component({
  selector: 'app-game',
  standalone: true,
  imports: [NgIf, GameSettingsComponent, MultipleChoiceComponent, RouterOutlet, RouterLink, RouterLinkActive, MainRoutingComponent],
  templateUrl: './game.component.html',
  styleUrl: './game.component.css'
})
export class GameComponent {


  constructor(private gameService: GameService, private route: ActivatedRoute) {}

  hasStarted = false;
  hasEnded = false;
  sessionID: number = 0;
  isValid: boolean = false;
  collectionID: number = Number(this.route.snapshot.paramMap.get('id'))
  questionType: string = "meanings"
  answerType: string = "term"
  isDiacritic: boolean = false
  numQuestions = 10
  numAnswers = 4
  EMPTY = -1
  settingLevel = 0

  
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
    let properties: GameProperties = {
      numQuestions: this.numQuestions, 
      numAnswers: this.numAnswers,
      questionType: this.questionType,
      answerType: this.answerType
    }
    let gameFilters: filters = {
      matching: "",
      isDiacritic: 0,
      grades: [],
      jlpt: [],
      strokes: [0, 0],
      frequnecy: [0, 0]
    }
    let game: Game = {
      gameProperties: properties,
      sessionID: this.sessionID,
      collectionID: this.collectionID,
      filters: gameFilters
    }
    this.gameService.createGame(game).subscribe(id => {
      this.sessionID = id;
      if (this.sessionID > 0) {
        this.hasStarted = true;
      } else {
        this.endGame();
      }
    });
    /*
    this.gameService.startGame(this.numQuestions, this.numAnswers).subscribe(id => {
      this.sessionID = id
      this.setQuestion()
    });
    */
  }

  setQuestion() {
    this.gameService.setQuestion(this.sessionID, this.questionType, this.answerType).subscribe(valid => {
      this.isValid = valid
      if (!this.isValid) {
        this.endGame()
        return;
      }
      this.setTerms()
    })
  }

  setTerms() {
    this.gameService.setTerms(this.sessionID, this.collectionID).subscribe(valid => this.hasStarted = valid)
  }

  endGame(): void {
    this.gameService.endGame(this.sessionID).subscribe(hasStarted => this.hasStarted = hasStarted);
  }
}
