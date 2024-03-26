import { Component } from '@angular/core';
import { GameService } from '../game.service';

@Component({
  selector: 'app-multiple-choice',
  standalone: true,
  imports: [],
  templateUrl: './multiple-choice.component.html',
  styleUrl: './multiple-choice.component.css'
})
export class MultipleChoiceComponent {

  constructor(private gameService: GameService) {}

  endGame(): void {
    this.gameService.endGame();
  }
}
