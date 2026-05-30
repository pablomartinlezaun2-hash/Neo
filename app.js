const STORAGE_KEY = 'fitpulse_workouts';

const workoutForm = document.querySelector('#workout-form');
const workoutList = document.querySelector('#workout-list');
const sessionsEl = document.querySelector('#sessions');
const minutesEl = document.querySelector('#minutes');
const kcalEl = document.querySelector('#kcal');
const resetBtn = document.querySelector('#reset');
const bmiForm = document.querySelector('#bmi-form');
const bmiResult = document.querySelector('#bmi-result');

let workouts = JSON.parse(localStorage.getItem(STORAGE_KEY) || '[]');

function saveWorkouts() {
  localStorage.setItem(STORAGE_KEY, JSON.stringify(workouts));
}

function render() {
  workoutList.innerHTML = '';

  workouts.forEach((w, i) => {
    const li = document.createElement('li');
    li.innerHTML = `<span>${w.type} · ${w.duration} min</span><span>${w.calories} kcal</span>`;

    li.addEventListener('click', () => {
      workouts.splice(i, 1);
      saveWorkouts();
      render();
    });

    workoutList.appendChild(li);
  });

  const totals = workouts.reduce(
    (acc, w) => ({
      sessions: acc.sessions + 1,
      minutes: acc.minutes + Number(w.duration),
      kcal: acc.kcal + Number(w.calories)
    }),
    { sessions: 0, minutes: 0, kcal: 0 }
  );

  sessionsEl.textContent = totals.sessions;
  minutesEl.textContent = totals.minutes;
  kcalEl.textContent = totals.kcal;
}

workoutForm.addEventListener('submit', (e) => {
  e.preventDefault();

  const type = document.querySelector('#type').value;
  const duration = Number(document.querySelector('#duration').value);
  const calories = Number(document.querySelector('#calories').value);

  workouts.push({ type, duration, calories });
  saveWorkouts();
  workoutForm.reset();
  render();
});

resetBtn.addEventListener('click', () => {
  workouts = [];
  saveWorkouts();
  render();
});

bmiForm.addEventListener('submit', (e) => {
  e.preventDefault();
  const weight = Number(document.querySelector('#weight').value);
  const height = Number(document.querySelector('#height').value);

  const bmi = weight / (height * height);
  let category = 'Peso normal';

  if (bmi < 18.5) category = 'Bajo peso';
  else if (bmi >= 25 && bmi < 30) category = 'Sobrepeso';
  else if (bmi >= 30) category = 'Obesidad';

  bmiResult.textContent = `IMC: ${bmi.toFixed(1)} (${category})`;
});

render();
