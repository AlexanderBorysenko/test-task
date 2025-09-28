import json
import time
import pika
from .configuration import (
    RABBITMQ_HOST,
    RABBITMQ_PORT,
    RABBITMQ_USERNAME,
    RABBITMQ_PASSWORD,
    REQUEST_QUEUE,
    RESPONSE_QUEUE,
)


def item_processor(item_id):
    print(f" [.] Processing item {item_id}")

    time.sleep(5)
    return {"id": item_id}


def item_request_queue_callback(channel, method, properties, body):
    print(" [x] Received %r" % body)

    message = json.loads(body)
    item_id = message["id"]

    result = item_processor(item_id)

    channel.basic_publish(
        exchange="", routing_key=RESPONSE_QUEUE, body=json.dumps(result)
    )

    channel.basic_ack(delivery_tag=method.delivery_tag)


def main():
    connection = pika.BlockingConnection(
        pika.ConnectionParameters(
            host=RABBITMQ_HOST,
            port=RABBITMQ_PORT,
            credentials=pika.PlainCredentials(RABBITMQ_USERNAME, RABBITMQ_PASSWORD),
        )
    )
    channel = connection.channel()

    channel.queue_declare(queue=REQUEST_QUEUE, durable=True)
    channel.queue_declare(queue=RESPONSE_QUEUE, durable=True)

    # Max 1 task per worker (simplest approach with blocking processing)
    channel.basic_qos(prefetch_count=1)

    channel.basic_consume(
        queue=REQUEST_QUEUE, on_message_callback=item_request_queue_callback
    )

    print(" [*] Worker started. Waiting for messages...")
    channel.start_consuming()


if __name__ == "__main__":
    main()
