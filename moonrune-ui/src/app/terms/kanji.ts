import { Term } from "./term";

export interface Cyrillic extends Term {
    readings: string[];
    romaji: string[];
    grade: string;
    jlpt: string;
    rank: number;
    strokes: number;
}