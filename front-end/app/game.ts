import { Participant } from './participant';
import { Prediction } from './prediction';

export class Game {
  status: string;
  scheduled: string;
  home: Participant;
  away: Participant;
  prediction: Prediction;
}
