import { Component } from '@angular/core';
import { GameService } from '../game.service';

@Component({
  selector: 'app-game-settings',
  standalone: true,
  imports: [],
  templateUrl: './game-settings.component.html',
  styleUrl: './game-settings.component.css'
})
export class GameSettingsComponent {
  constructor(private gameService: GameService) {}

  startGame(): void {
    this.gameService.startGame();
  }
}
