import { Routes } from '@angular/router';
import { DictionaryComponent } from './dictionary/dictionary/dictionary.component';
import { GameComponent } from './game/game/game.component';
import { MainComponent } from './general/main/main.component';

export const routes: Routes = [
    {path: "dictionary", component: DictionaryComponent}, 
    {path: "quiz", component: GameComponent},
    {path: "mainpage", component: MainComponent},
    { path: '',   redirectTo: 'mainpage', pathMatch: 'full' },
  //{ path: '**', component: PageNotFoundComponent }
];
