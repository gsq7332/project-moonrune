import { Routes } from '@angular/router';
import { DictionaryComponent } from './dictionary/dictionary/dictionary.component';
import { GameComponent } from './game/game/game.component';
import { MainComponent } from './general/main/main.component';
import { SignUpComponent } from './user/sign-up/sign-up.component';
import { SignInComponent } from './user/sign-in/sign-in.component';
import { UserPageComponent } from './user/user-page/user-page.component';

export const routes: Routes = [
    {path: "dictionary/:id", component: DictionaryComponent},
    {path: "quiz/:id", component: GameComponent}, 
    {path: "mainpage", component: MainComponent},
    {path: "sign-up", component: SignUpComponent},
    {path: "sign-in", component: SignInComponent},
    {path: "users/:username", component: UserPageComponent},
    { path: '',   redirectTo: 'mainpage', pathMatch: 'full' },
  //{ path: '**', component: PageNotFoundComponent }
];
