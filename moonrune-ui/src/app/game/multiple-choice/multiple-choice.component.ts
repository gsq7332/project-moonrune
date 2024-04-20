import { Component, Output, Input } from '@angular/core';
import { GameService } from '../game.service';
import { EventEmitter } from '@angular/core';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-multiple-choice',
  standalone: true,
  imports: [NgIf],
  templateUrl: './multiple-choice.component.html',
  styleUrl: './multiple-choice.component.css'
})
export class MultipleChoiceComponent {
  @Input() collection ?: string;
  @Output() endGameEvent = new EventEmitter();
  hasEnded: boolean = false;

  constructor(private gameService: GameService) {}

  ngOnInit() {
    
    this.generateQuestion();
  }

  generateQuestion() {
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
