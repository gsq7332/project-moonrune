import { Component, Output, Input } from '@angular/core';
import { GameService } from '../game.service';
import { EventEmitter } from '@angular/core';
import { NgFor, NgIf } from '@angular/common';
import { Term } from '../../terms/term';
import { TermServiceService } from '../../term-service.service';
import { McOptionComponent } from '../mc-option/mc-option.component';
import { Kanji } from '../../terms/kanji';

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
  @Output() endGameEvent = new EventEmitter();
  hasEnded: boolean = false;
  terms: Term[] = [];
  termString: string[] = [];

  constructor(private gameService: GameService, private termService: TermServiceService) {}

  ngOnInit() {
    
    this.generateQuestion();
  }

  generateQuestion() {
    this.termService.getRandomTerms(this.numQuestions).subscribe(terms => this.terms = terms);
    
  }

  answerQuestion() {
    // call the service thing
    // have the api handle behavior
  }

  endGame(): void {
    this.endGameEvent.emit();
  }
}
