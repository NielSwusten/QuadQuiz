import {Component, OnInit} from '@angular/core';
import {RouterLinkActive, RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';
import {Questions} from '../entities/questions';
import {QuestionsService} from '../services/questions/questions.service';

@Component({
  selector: 'app-game',
  imports: [
    RouterLinkActive,
    RouterModule,
    CommonModule
  ],
  templateUrl: './game.html',
  standalone: true,
  styleUrl: './game.css'
})
export class Game implements OnInit {
  questions: Questions[] = [];
  currentQuestionIndex: number = 0;
  selectedAnswers: string[] = [];
  isLoading: boolean = true;
  error: string = '';
  timer: number = 30; // 30 seconden per vraag
  timerInterval: any;

  constructor(private questionsService: QuestionsService) {
  }

  ngOnInit() {
    this.loadQuestions();
  }

  loadQuestions() {
    this.isLoading = true;
    this.questionsService.getQuestions().subscribe({
      next: (data) => {
        this.questions = data;
        this.selectedAnswers = new Array(data.length).fill('');
        this.isLoading = false;
        this.startTimer();
      },
      error: (err) => {
        this.error = 'Failed to load questions';
        this.isLoading = false;
        console.error('Error loading questions:', err);
      }
    });
  }
  selectAnswer(answer: string) {
    this.selectedAnswers[this.currentQuestionIndex] = answer;
    this.nextQuestion()
  }

  nextQuestion() {
    if (this.currentQuestionIndex < this.questions.length - 1) {
      this.currentQuestionIndex++;
      this.resetTimer();
    } else {
      this.finishQuiz();
    }
  }

  getCurrentQuestion(): Questions | null {
    return this.questions[this.currentQuestionIndex] || null;
  }

  startTimer() {
    this.timer = 30;
    this.timerInterval = setInterval(() => {
      this.timer--;
      if (this.timer <= 0) {
        this.nextQuestion();
      }
    }, 1000);
  }

  resetTimer() {
    clearInterval(this.timerInterval);
    this.startTimer();
  }

  finishQuiz() {
    clearInterval(this.timerInterval);
    // Hier zou je de antwoorden kunnen checken via de API
    console.log('Selected answers:', this.selectedAnswers);
    alert('Quiz finished!');
  }

  ngOnDestroy() {
    if (this.timerInterval) {
      clearInterval(this.timerInterval);
    }
  }

}
