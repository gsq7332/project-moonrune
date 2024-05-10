import { Routes } from '@angular/router';
import { DictionaryComponent } from './dictionary/dictionary/dictionary.component';
import { GameComponent } from './game/game/game.component';
import { MainComponent } from './general/main/main.component';

export const routes: Routes = [
    {path: "dictionary/:id", component: DictionaryComponent},
    {path: "quiz/:id", component: GameComponent}, 
    {path: "mainpage", component: MainComponent},
    { path: '',   redirectTo: 'mainpage', pathMatch: 'full' },
  //{ path: '**', component: PageNotFoundComponent }
];
