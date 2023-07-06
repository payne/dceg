import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'; // Import it up here
import { Observable } from 'rxjs'; // Import it up here
import { Todo} from './todo/todo'; // Import the Todo interface

@Injectable({
  providedIn: 'root'
})
export class TodoService {

  // Use HttpClient to communicate with backend
  constructor(private readonly http: HttpClient) {}

  // Get all Todos from /api/todos
  getTodos(): Observable<Todo[]> {
    return this.http.get<Todo[]>('/api/todos');
  }
}
