import { filters } from "../terms/filters";
import { GameProperties } from "./game-properties";

export interface Game {
    gameProperties: GameProperties,
    sessionID: number,
    collectionID: number,
    filters: filters
}