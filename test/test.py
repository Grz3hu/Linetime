#!/usr/bin/env python3
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.firefox.options import Options
import random
import string
import time

# Replace the path with the actual path to your chromedriver executable
gecko_driver_path = '/nix/store/fq2c9y5gz53cis89liwf3lv91r3hfmc8-geckodriver-0.33.0/bin/geckodriver'

# Run firefox headless
firefox_options = Options()
firefox_options.add_argument("--headless")
# Create a new instance of the Chrome WebDriver
driver = webdriver.Firefox(executable_path=gecko_driver_path, options=firefox_options)

# URL to access
url = "http://192.168.49.2/"


def register(random_name):
    driver.get(url + "register")
    # Find the inputs
    name_input = driver.find_element(By.CSS_SELECTOR, 'input[placeholder="Enter your name"]')
    username_input = driver.find_element(By.CSS_SELECTOR, 'input[placeholder="Enter your username"]')
    email_input = driver.find_element(By.CSS_SELECTOR, 'input[placeholder="Enter your e-mail address"]')
    password_input = driver.find_element(By.CSS_SELECTOR, 'input[placeholder="Enter your password"]')
    # Fill the form with the generated name
    name_input.send_keys(random_name)
    username_input.send_keys(random_name)
    email_input.send_keys(random_name + "@test.com")
    password_input.send_keys(random_name)
    # Submit
    submit_button = driver.find_element(By.XPATH, '//button[text()="Submit"]')
    submit_button.click()


def login(random_name):
    driver.get(url + "login")
    # Find the inputs
    email_input = driver.find_element(By.CSS_SELECTOR, 'input[placeholder="Enter your e-mail or username"]')
    password_input = driver.find_element(By.CSS_SELECTOR, 'input[placeholder="Enter your password"]')
    # Fill the form with the generated name
    email_input.send_keys(random_name + "@test.com")
    password_input.send_keys(random_name)
    # Submit
    login_button = driver.find_element(By.XPATH, '//button[text()="Login"]')
    login_button.click()


def add_timeline(title):
    driver.get(url + "add_timeline")
    # Find the inputs
    title_input = driver.find_element(By.CSS_SELECTOR, 'input[placeholder="Enter title"]')
    # Fill the form with the generated name
    title_input.send_keys(title)
    # Submit
    submit_button = driver.find_element(By.XPATH, '//button[text()="Submit"]')
    submit_button.click()


def view_timeline(title):
    driver.get(url + "browse_timelines")
    target_row = WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.XPATH, f'//td[text()="{title}"]/..')))
    view_timeline_link = target_row.find_element(By.XPATH, './/a[text()="View Timeline"]')
    view_timeline_link.click()
    return driver.current_url.split('/')[-1]


def add_event(timeline_id, text):
    driver.get(url + "add_event/" + timeline_id)
    date_input = driver.find_element(By.CSS_SELECTOR, 'input[placeholder="Enter date"]')
    cardtitle_input = driver.find_element(By.CSS_SELECTOR, 'input[placeholder="Enter card title"]')
    cardsubtitle_input = driver.find_element(By.CSS_SELECTOR, 'input[placeholder="Enter card subtitle"]')
    detailedtext_input = driver.find_element(By.CSS_SELECTOR, 'textarea[placeholder="Enter detailed text"]')
    # Fill the form with the generated name
    date_input.send_keys("1.08.2024")
    cardtitle_input.send_keys(text)
    cardsubtitle_input.send_keys(text)
    detailedtext_input.send_keys(text)
    # Submit
    submit_button = driver.find_element(By.XPATH, '//button[text()="Submit"]')
    driver.execute_script("arguments[0].scrollIntoView();", submit_button)
    submit_button.click()


def delete_timeline(title):
    driver.get(url + "browse_timelines")
    target_row = WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.XPATH, f'//td[text()="{title}"]/..')))
    view_timeline_link = target_row.find_element(By.XPATH, './/button[text()="Delete"]')
    view_timeline_link.click()


try:
    random_letters = ''.join(random.choices(string.ascii_letters, k=8))
    register(random_letters)
    login(random_letters)
    add_timeline(random_letters)
    timeline_id = view_timeline(random_letters)
    add_event(timeline_id, random_letters)
    delete_timeline(random_letters)
finally:
    driver.quit()
