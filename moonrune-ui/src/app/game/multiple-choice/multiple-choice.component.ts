import { Component, Output, Input } from '@angular/core';
import { GameService } from '../game.service';
import { EventEmitter } from '@angular/core';
import { NgIf } from '@angular/common';
import { Term } from '../../terms/term';
import { TermServiceService } from '../../term-service.service';

@Component({
  selector: 'app-multiple-choice',
  standalone: true,
  imports: [NgIf],
  templateUrl: './multiple-choice.component.html',
  styleUrl: './multiple-choice.component.css'
})
export class MultipleChoiceComponent {
  @Input() collection ?: string;
  @Input() numQuestions: number = 0;
  @Output() endGameEvent = new EventEmitter();
  hasEnded: boolean = false;
  terms: Term[]|undefined;

  constructor(private gameService: GameService, private termService: TermServiceService) {}

  ngOnInit() {
    
    this.generateQuestion();
  }

  generateQuestion() {
    this.termService.getRandomTerms(this.numQuestions).subscribe(terms => this.terms = terms);
    
    // needed behavior:
    // get requested properties from settings (part of settings thing)
    // get list of allowed terms (i.e. the collection)
  }

  answerQuestion() {
    // call the service thing
    // have the api handle behavior
  }

  endGame(): void {
    this.endGameEvent.emit();
  }
}
