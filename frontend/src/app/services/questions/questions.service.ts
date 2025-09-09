import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import {Questions} from '../../entities/questions';

@Injectable({
  providedIn: 'root'
})
export class QuestionsService {

  url = `http://localhost:8080/api/questions`;

  constructor(private httpClient: HttpClient) { }

  getQuestions(): Observable<Questions[]> {
    return this.httpClient.get<Questions[]>(this.url);
  }







}

