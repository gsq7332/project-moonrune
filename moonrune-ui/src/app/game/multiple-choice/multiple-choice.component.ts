import { Component, Output, Input } from '@angular/core';
import { GameService } from '../game.service';
import { EventEmitter } from '@angular/core';
import { NgFor, NgIf } from '@angular/common';
import { Term } from '../../terms/term';
import { McOptionComponent } from '../mc-option/mc-option.component';

@Component({
  selector: 'app-multiple-choice',
  standalone: true,
  imports: [NgIf, NgFor, McOptionComponent],
  templateUrl: './multiple-choice.component.html',
  styleUrl: './multiple-choice.component.css'
})
export class MultipleChoiceComponent {
  @Input() collection ?: string;
  @Input() numQuestions: number = 0;
  @Input() questionType ?: string;
  @Input() answerType ?: string;
  @Input() sessionID ?: number;
  @Output() endGameEvent = new EventEmitter();
  isCorrect: boolean = false;
  hasEnded: boolean = false;
  terms: string[] = [];
  question: string = "";

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
      this.gameService.generateQuestion(this.sessionID).subscribe(terms => this.terms = terms);
      this.question = this.terms[this.terms.length-1]
    }
  }

  answerQuestion(answer: string) {
    if (this.sessionID != undefined) {
      this.gameService.checkQuestion(this.sessionID, answer).subscribe(correct => this.isCorrect = correct);
    }
  }

  endGame(): void {
    if (this.sessionID != null) {
      this.gameService.endGame(this.sessionID); 
    }
    this.endGameEvent.emit();
  }
}
