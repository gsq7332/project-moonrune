import { Component, Output, Input } from '@angular/core';
import { GameService } from '../game.service';
import { EventEmitter } from '@angular/core';
import { NgFor, NgIf } from '@angular/common';
import { Term } from '../../terms/term';
import { McOptionComponent } from '../mc-option/mc-option.component';
import { concatMap, timer } from 'rxjs';
import { randomInt } from 'node:crypto';

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
  progress: number[] = [0, 0]

  TIME = 1000

  constructor(private gameService: GameService) {}

  ngOnInit() {
    if (this.sessionID != undefined) {
      this.generateQuestion()
    } else {
      this.endGame()
    }
  }

  generateQuestion() {
    if (this.sessionID != undefined) {
      this.gameService.generateQuestion(this.sessionID).subscribe(terms => {
        this.terms = terms.slice(0, -1)
        let currentIdx = this.terms.length
        while (currentIdx != 0) {
          let randomIdx = Math.floor(Math.random() * currentIdx)
          currentIdx--;
          [this.terms[currentIdx], this.terms[randomIdx]] = [this.terms[randomIdx], this.terms[currentIdx]]
        }
        this.question = terms[terms.length-1]
        this.hasAnswered = false;
      });
    }
  }

  answerQuestion(answer: string) {
    if (this.sessionID == undefined) return;
    this.gameService.checkQuestion(this.sessionID, answer).subscribe(correct => {
      this.isCorrect = correct
      if (this.sessionID == undefined) return;
      this.gameService.getProgress(this.sessionID).subscribe(progress => this.progress = progress)
      this.hasAnswered = true;
  });
    const activityObs = this.gameService.checkActivity(this.sessionID);
    timer(this.TIME).pipe(concatMap(() => activityObs)).subscribe(ended => {
      this.hasEnded = ended
    if (!this.hasEnded) {
      this.generateQuestion();
    } else {
      this.hasAnswered = false;
    }
  }); 
  }

  endGame(): void {
    this.endGameEvent.emit();
  }
}
