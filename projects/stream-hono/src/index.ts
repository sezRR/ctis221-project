import { Hono } from 'hono'
import { streamText } from 'hono/streaming'

const app = new Hono()

app.get('/', (c) => {
    return c.text('Hello Hono!')
})

app.get('/stream/1', (c) => {
    return streamText(c, async (stream) => {
        // Write a text with a new line ('\n').
        await stream.writeln('Hello')
        // Wait 1 second.
        await stream.sleep(1000)
        // Write a text without a new line.
        await stream.write(`Hono!`)
    })
})

app.get("/stream/2", (c) => {
    return streamText(c, async (stream) => {
        for (let i = 0; i < 100; i++) {
            await stream.writeln(`Hello ${i}`)
            await stream.sleep(10)
        }
    })
})

export default app
