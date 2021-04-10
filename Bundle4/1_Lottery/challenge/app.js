"use strict";

const express = require("express");
const path = require("path");

const app = express();
const port = process.env.PORT || "3000";

app.use(express.static(path.join(__dirname, "public")));
app.use(express.json());

app.get("/", (req, res) => {
  res.redirect("/index.html");
});

const FLAG = process.env.FLAG ?? "CTF{dummyflag}";

const MAX_CHOICE = 10000;

function generate_lotto_numbers() {
  const numbers = [];

  for (let i = 0; i < 6; i++) {
    const rand = Math.random();
    const number = Math.floor(rand * MAX_CHOICE);
    console.log(rand, number);
    numbers.push(number);
  }

  return numbers;
}


let counter = 0;
function getLoserMessage(numbers) {
  const lotto_numbers = JSON.stringify(numbers)
  const messages = [
    `Today is not your lucky day :( \nthe numbers were: ${lotto_numbers}`,
    `Hmm that is not right, you should have gone with ${lotto_numbers}`,
    `And the winner is: not you, or was your guess ${lotto_numbers}?`,
    `Did you know gambling addiction is a thing? Winning numbers: ${lotto_numbers}`,
    `Winner: The people who guessed ${lotto_numbers}\nLoser: You`,
    `C'mon, how hard can this be? You just had to input ${lotto_numbers}, takes like 5 seconds`
  ]
  return messages[counter++ % messages.length]
}

app.post("/make_guess", (req, res) => {
  const { guess } = req.body;

  if (guess === undefined || guess.length != 6) {
    res.status(400).send("You didn't make a guess :(");
    return;
  }

  const lotto_numbers = generate_lotto_numbers();

  for (let i = 0; i < lotto_numbers.length; i++) {
    if (lotto_numbers[i] !== guess[i]) {
      res
        .status(200)
        .send(
          getLoserMessage(lotto_numbers)
        );
      return;
    }
  }

  res.status(200).send(`Congratulations, here is your price: ${FLAG}`);
});

app.listen(port, () => {
  console.log(`Listening to requests on http://localhost:${port}`);
});
