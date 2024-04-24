import { Component, Output, Input } from '@angular/core';
import { GameService } from '../game.service';
import { EventEmitter } from '@angular/core';
import { NgFor, NgIf } from '@angular/common';
import { Term } from '../../terms/term';
import { McOptionComponent } from '../mc-option/mc-option.component';
import { concatMap, timer } from 'rxjs';

@Component({
  selector: 'app-multiple-choice',
  standalone: true,
  imports: [NgIf, NgFor, McOptionComponent],
  templateUrl: './multiple-choice.component.html',
  styleUrl: './multiple-choice.component.css'
})
export class MultipleChoiceComponent {
  @Input() sessionID ?: number;
  @Output() endGameEvent = new EventEmitter();
  isCorrect: boolean = false;
  hasEnded: boolean = false;
  hasAnswered: boolean = false;
  terms: string[] = [];
  question: string = "";

  TIME = 1000

  constructor(private gameService: GameService) {}

  ngOnInit() {
    if (this.sessionID != undefined) {
      this.generateQuestion(false)
    } else {
      this.endGame()
    }
  }

  generateQuestion(delay: boolean) {
    if (this.sessionID != undefined) {
      this.gameService.generateQuestion(this.sessionID).subscribe(terms => {
        this.terms = terms
        this.question = this.terms[this.terms.length-1]
      });
    }
  }

  answerQuestion(answer: string) {
    if (this.sessionID == undefined) return;
    this.hasAnswered = true;
    this.gameService.checkQuestion(this.sessionID, answer).subscribe(correct => this.isCorrect = correct);
    const activityObs = this.gameService.checkActivity(this.sessionID);
    timer(this.TIME).pipe(concatMap(() => activityObs)).subscribe(ended => this.hasEnded = ended);
    this.hasAnswered = false;
    if (!this.hasEnded) {
      this.generateQuestion(true);
    }
  }

  endGame(): void {
    if (this.sessionID != null) {
      this.gameService.endGame(this.sessionID); 
    }
    this.endGameEvent.emit();
  }
}
