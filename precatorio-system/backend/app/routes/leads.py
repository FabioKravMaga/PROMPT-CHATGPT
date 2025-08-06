from fastapi import APIRouter
from pydantic import BaseModel, EmailStr
from typing import List

router = APIRouter()

class Lead(BaseModel):
    id: int
    name: str
    email: EmailStr
    phone: str
    status: str = "new"

# In-memory storage for example
LEADS_DB: List[Lead] = []

@router.post("/", response_model=Lead)
async def create_lead(lead: Lead):
    LEADS_DB.append(lead)
    return lead

@router.get("/", response_model=List[Lead])
async def list_leads():
    return LEADS_DB
