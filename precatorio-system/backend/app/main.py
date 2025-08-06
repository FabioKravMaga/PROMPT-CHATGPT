from fastapi import FastAPI
from .routes import leads, proposals

app = FastAPI(title="Precatório Automation API")

app.include_router(leads.router, prefix="/leads", tags=["leads"])
app.include_router(proposals.router, prefix="/proposals", tags=["proposals"])

@app.get("/", tags=["health"])
async def health_check():
    return {"status": "ok"}
