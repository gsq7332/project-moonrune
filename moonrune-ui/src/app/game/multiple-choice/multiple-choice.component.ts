import { Component, Output } from '@angular/core';
import { GameService } from '../game.service';
import { EventEmitter } from '@angular/core';
import { log } from 'console';

@Component({
  selector: 'app-multiple-choice',
  standalone: true,
  imports: [],
  templateUrl: './multiple-choice.component.html',
  styleUrl: './multiple-choice.component.css'
})
export class MultipleChoiceComponent {

  @Output() endGameEvent = new EventEmitter();

  constructor(private gameService: GameService) {}

  ngOnInit() {
    
  }

  endGame(): void {
    this.endGameEvent.emit();
  }
}
